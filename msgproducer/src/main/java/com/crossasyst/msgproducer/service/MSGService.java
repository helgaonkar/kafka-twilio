package com.crossasyst.msgproducer.service;

import com.crossasyst.msgproducer.entity.UserEntity;
import com.crossasyst.msgproducer.mapper.UserMapper;
import com.crossasyst.msgproducer.model.UserModel;
import com.crossasyst.msgproducer.model.UserResponse;
import com.crossasyst.msgproducer.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class MSGService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Value("${topic.name.producer}")
    private String topicName;

    private KafkaTemplate<String, String> kafkaTemplate;


    public UserResponse createUser(UserModel userModel){
        UserEntity userEntity=null;
        userEntity=userMapper.modelToEntity(userModel);
        userRepository.save(userEntity);

        UserResponse userResponse =new UserResponse();
        userResponse.setId(userEntity.getId());

        log.info("Posting.....");
        return userResponse;

    }

    @Scheduled(cron = "0/10 * * * * *")
    public void findByDayMonth()
    {
        int count=0;
        List<String> entityList =  userRepository.findAllByDayMonth();
        for (String mail:entityList )
        {
            kafkaTemplate.send(topicName, mail);

        }
        ++count;
        log.info("Fetching....."+count);


    }
}
