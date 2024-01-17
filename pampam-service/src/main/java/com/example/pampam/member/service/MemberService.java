package com.example.pampam.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.pampam.common.BaseResponse;
import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.member.model.request.*;
import com.example.pampam.member.model.response.*;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.member.repository.SellerRepository;
import com.example.pampam.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final EmailVerifyService emailVerifyService;
    private final AmazonS3 s3;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    @Value("${project.upload.path}")
    private String uploadPath;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
@Transactional
    public BaseResponse consumerSignup(ConsumerSignupReq consumerSignupReq) {
        if (consumerRepository.findByEmail(consumerSignupReq.getEmail()).isPresent()) {
            return BaseResponse.failResponse(7000, "요청실패");
        }
        Consumer consumer = consumerRepository.save(Consumer.builder()
                .email(consumerSignupReq.getEmail())
                .consumerPW(passwordEncoder.encode(consumerSignupReq.getConsumerPW()))
                .consumerName(consumerSignupReq.getConsumerName())
                .consumerAddr(consumerSignupReq.getConsumerAddr())
                .consumerPhoneNum(consumerSignupReq.getConsumerPhoneNum())
                .authority("CONSUMER")
                .socialLogin(false)
                .status(false)
                .build());

        String accessToken = JwtUtils.generateAccessToken(consumer, secretKey, expiredTimeMs);

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(consumer.getEmail())
                .authority(consumer.getAuthority())
                .accessToken(accessToken)
                .build();

        sendEmail(sendEmailReq);
        Optional<Consumer> result = consumerRepository.findByEmail(consumer.getEmail());

        if (result.isPresent()){
            consumer = result.get();
        }

        ConsumerSignupRes consumerSignupRes = ConsumerSignupRes.builder()
                .consumerIdx(consumer.getConsumerIdx())
                .email(consumer.getEmail())
                .consumerPW(consumer.getConsumerPW())
                .consumerName(consumer.getConsumerName())
                .consumerAddr(consumer.getConsumerAddr())
                .consumerPhoneNum(consumer.getConsumerPhoneNum())
                .authority(consumer.getAuthority())
                .socialLogin(consumer.getSocialLogin())
                .status(consumer.getStatus())
                .build();

        BaseResponse baseResponse = BaseResponse.successResponse("요청성공",consumerSignupRes);

        return baseResponse;

    }
    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    public String saveFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;
//        File saveFile = new File(uploadPath, saveFileName);

        try {
//            file.transferTo(saveFile);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3.putObject(bucket, saveFileName.replace(File.separator, "/"), file.getInputStream(), metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s3.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
    }
    public BaseResponse sellerSignup(SellerSignupReq sellerSignupReq, MultipartFile image) {

        String saveFileName = saveFile(image);

        if (sellerRepository.findByEmail(sellerSignupReq.getEmail()).isPresent()) {
            return BaseResponse.failResponse(7000, "요청실패");
        }
        Seller seller = sellerRepository.save(Seller.builder()
                .email(sellerSignupReq.getEmail())
                .sellerPW(passwordEncoder.encode(sellerSignupReq.getSellerPW()))
                .sellerName(sellerSignupReq.getSellerName())
                .sellerAddr(sellerSignupReq.getSellerAddr())
                .sellerPhoneNum(sellerSignupReq.getSellerPhoneNum())
                .sellerBusinessNumber(sellerSignupReq.getSellerBusinessNumber())
                .authority("SELLER")
                .status(false)
                        .image(saveFileName)
                .build());

        String accessToken = JwtUtils.generateAccessToken(seller, secretKey, expiredTimeMs);

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(seller.getEmail())
                .authority(seller.getAuthority())
                .accessToken(accessToken)
                .build();

        sendEmail(sendEmailReq);
        SellerSignupRes sellerSignupRes = SellerSignupRes.builder()
                .sellerIdx(seller.getSellerIdx())
                .email(seller.getEmail())
                .sellerPW(seller.getSellerPW())
                .sellerName(seller.getSellerName())
                .sellerAddr(seller.getSellerAddr())
                .sellerPhoneNum(seller.getSellerPhoneNum())
                .sellerPhoneNum(seller.getSellerPhoneNum())
                .authority(seller.getAuthority())
                .status(seller.getStatus())
                .sellerBusinessNumber(seller.getSellerBusinessNumber())
                .image(seller.getImage())
                .build();

        BaseResponse baseResponse = BaseResponse.successResponse("요청성공", sellerSignupRes);

        return baseResponse;

    }

    public BaseResponse consumerLogin(ConsumerLoginReq consumerLoginReq) {
        Optional<Consumer> consumer = consumerRepository.findByEmail(consumerLoginReq.getEmail());

        if (consumer.isPresent()) {
            if (passwordEncoder.matches(consumerLoginReq.getPassword(), consumer.get().getPassword())) {

                ConsumerLoginRes consumerLoginRes = ConsumerLoginRes.builder()
                        .token(JwtUtils.generateAccessToken(consumer.get(), secretKey, expiredTimeMs))
                        .build();

                BaseResponse baseResponse = BaseResponse.successResponse("요청성공", consumerLoginRes);


                return baseResponse;
            }else {
                return BaseResponse.failResponse(7000, "요청실패");
            }
        }
        return BaseResponse.failResponse(7000, "요청실패");
    }

    public BaseResponse sellerLogin(SellerLoginReq sellerLoginReq) {
        Optional<Seller> seller = sellerRepository.findByEmail(sellerLoginReq.getEmail());

        if (seller.isPresent()) {
            if (passwordEncoder.matches(sellerLoginReq.getPassword(), seller.get().getPassword())) {

                SellerLoginRes sellerLoginRes = SellerLoginRes.builder()
                        .token(JwtUtils.generateAccessToken(seller.get(), secretKey, expiredTimeMs))
                        .build();

                BaseResponse baseResponse = BaseResponse.successResponse("요청성공",sellerLoginRes);


                return  baseResponse;
            }else {
                return BaseResponse.failResponse(7000, "요청실패");
            }
        }
        return BaseResponse.failResponse(7000, "요청실패");
    }

    public BaseResponse consumerUpdate(ConsumerUpdateReq consumerUpdateReq) {
        Optional<Consumer> result = consumerRepository.findByEmail(consumerUpdateReq.getEmail());
        Consumer consumer = null;
        if (result.isPresent()) {
            consumer = result.get();
            consumer = Consumer.builder()
                    .consumerIdx(consumer.getConsumerIdx())
                    .email(consumerUpdateReq.getEmail())
                    .consumerPW(passwordEncoder.encode(consumerUpdateReq.getConsumerPW()))
                    .consumerName(consumerUpdateReq.getConsumerName())
                    .consumerAddr(consumerUpdateReq.getConsumerAddr())
                    .consumerPhoneNum(consumerUpdateReq.getConsumerPhoneNum())
                    .authority(consumer.getAuthority())
                    .socialLogin(consumer.getSocialLogin())
                    .status(consumer.getStatus())
                    .build();
            consumerRepository.save(consumer);

            ConsumerUpdateRes consumerUpdateRes = ConsumerUpdateRes.builder()
                    .consumerIdx(consumer.getConsumerIdx())
                    .email(consumer.getEmail())
                    .consumerPW(consumer.getConsumerPW())
                    .consumerName(consumer.getConsumerName())
                    .consumerAddr(consumer.getConsumerAddr())
                    .consumerPhoneNum(consumer.getConsumerPhoneNum())
                    .socialLogin(consumer.getSocialLogin())
                    .authority(consumer.getAuthority())
                    .status(consumer.getStatus())
                    .build();

            BaseResponse baseResponse = BaseResponse.successResponse("요청성공", consumerUpdateRes);


            return baseResponse;
        } else {
            return BaseResponse.failResponse(7000, "요청실패");
        }
    }

    public BaseResponse sellerUpdate(SellerUpdateReq sellerUpdateReq) {
        Optional<Seller> result = sellerRepository.findByEmail(sellerUpdateReq.getEmail());
        Seller seller = null;
        if (result.isPresent()) {
            seller = result.get();
            seller = Seller.builder()
                    .sellerIdx(seller.getSellerIdx())
                    .email(sellerUpdateReq.getEmail())
                    .sellerPW(passwordEncoder.encode(sellerUpdateReq.getSellerPW()))
                    .sellerName(sellerUpdateReq.getSellerName())
                    .sellerAddr(sellerUpdateReq.getSellerAddr())
                    .sellerPhoneNum(sellerUpdateReq.getSellerPhoneNum())
                    .authority(seller.getAuthority())
                    .sellerBusinessNumber(sellerUpdateReq.getSellerBusinessNumber())
                    .status(seller.getStatus())
                    .build();
            sellerRepository.save(seller);

            SellerUpdateRes sellerUpdateRes = SellerUpdateRes.builder()
                    .sellerIdx(seller.getSellerIdx())
                    .email(seller.getEmail())
                    .sellerPW(seller.getSellerPW())
                    .sellerName(seller.getSellerName())
                    .sellerAddr(seller.getSellerAddr())
                    .sellerPhoneNum(seller.getSellerPhoneNum())
                    .sellerBusinessNumber(seller.getSellerBusinessNumber())
                    .authority(seller.getAuthority())
                    .status(seller.getStatus())
                    .build();

            BaseResponse baseResponse = BaseResponse.successResponse("요청성공", sellerUpdateRes);


            return baseResponse;

        } else {
            return null;
        }

    }

    public BaseResponse consumerDelete(ConsumerDeleteReq consumerDeleteReq) {
        Optional<Consumer> result = consumerRepository.findByEmail(consumerDeleteReq.getEmail());

        if (result.isPresent()) {
            consumerRepository.delete(Consumer.builder()
                    .consumerIdx(result.get().getConsumerIdx()).build());

            ConsumerDeleteRes consumerDeleteRes = ConsumerDeleteRes.builder().email(consumerDeleteReq.getEmail()).build();

            return BaseResponse.successResponse("요청성공", consumerDeleteRes);
        }
        return BaseResponse.failResponse(7000, "요청실패");

    }
    public BaseResponse sellerDelete(SellerDeleteReq sellerDeleteReq) {
        Optional<Seller> result = sellerRepository.findByEmail(sellerDeleteReq.getEmail());

        if (result.isPresent()) {
            sellerRepository.delete(result.get());

            SellerDeleteRes sellerDeleteRes = SellerDeleteRes.builder().email(sellerDeleteReq.getEmail()).build();
            BaseResponse baseResponse = BaseResponse.successResponse("요청성공", sellerDeleteRes);

            return baseResponse;
        }
        return BaseResponse.failResponse(7000, "요청실패");

    }
    public void sendEmail(SendEmailReq sendEmailReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendEmailReq.getEmail());
        message.setSubject("로컬푸드 pampam 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        message.setText("http://localhost:8080/member/confirm?email="
                + sendEmailReq.getEmail()
                + "&authority=" + sendEmailReq.getAuthority()
                + "&token=" + uuid
                + "&jwt=" + sendEmailReq.getAccessToken()
        );
        emailSender.send(message);
        emailVerifyService.create(sendEmailReq.getEmail(), uuid);
    }


    public Consumer getMemberByConsumerID(String email) {
        Optional<Consumer> result = consumerRepository.findByEmail(email);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    //
    public Seller getMemberBySellerID(String email) {
        Optional<Seller> result = sellerRepository.findByEmail(email);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Consumer> result = consumerRepository.findByEmail(email);
        Consumer member = null;
        if (result.isPresent())
            member = result.get();

        return member;
    }
}
