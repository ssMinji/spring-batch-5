package com.ezwel.esp.batch.sample.test.chunk.processor;

import com.ezwel.esp.batch.sample.test.domain.User;
import com.ezwel.esp.batch.sample.test.domain.UserDetail;
import com.ezwel.esp.batch.sample.test.mapper.UserDetailMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@StepScope
public class ChunkParallelItemProcessor implements ItemProcessor<User, User> {

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public User process(User item) throws Exception {
        int id = item.getId();
        item.setStatus("F"); // 상태 'F'로 변경
//        System.out.println("=======================Processor " + item.getId() + "===============================");
        LocalDate today = LocalDate.now();
        List<UserDetail> userDetail = userDetailMapper.selectUserDetailById(id);
        String activeStatus = "F";
        for (UserDetail userDetailItem : userDetail) {
            String empId = userDetailItem.getEmp_id();
            LocalDate endDate = LocalDate.parse("2024-10-01");
            userDetailMapper.updateEndDate(empId, endDate); // endDate 변경

            userDetailMapper.updateActiveStatus(empId, activeStatus);
        }

        return item;
    }

}
