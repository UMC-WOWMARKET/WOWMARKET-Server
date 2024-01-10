package wowmarket.wow_server.detail.project.service;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandDetailRequestDto;
import wowmarket.wow_server.detail.project.dto.OrderFormRequestDto;
import wowmarket.wow_server.detail.project.dto.OrderRequestDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProjectRepository projectRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderQuestionRepository orderQuestionRepository;
    private final OrderAnswerRepository orderAnswerRepository;


    public OrderService(ProjectRepository projectRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ItemRepository itemRepository, UserRepository userRepository, OrderQuestionRepository orderQuestionRepository, OrderAnswerRepository orderAnswerRepository) {
        this.projectRepository = projectRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.orderQuestionRepository = orderQuestionRepository;
        this.orderAnswerRepository = orderAnswerRepository;
    }


    public ResponseEntity createOrderForm(Long project_id, OrderFormRequestDto requestDto) {
        Project project = projectRepository.findByProject_Id(project_id);
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        //사용자의 대학교가 프로젝트의 대학교와 일치하지 않으면
        if (!Objects.equals(user.getUniv(), project.getUser().getUniv()))
        {
            //에러 반환
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Orders order = Orders.builder()
                .project(project)
                .user(user)
                .receiver(requestDto.getReceiver())
                .zipcode(requestDto.getZipcode())
                .address(requestDto.getAddress())
                .address_detail(requestDto.getAddress_detail())
                .phone(requestDto.getPhone())
                .depositor(requestDto.getDepositor())
                .depositTime(requestDto.getDepositTime())
                .bank(requestDto.getBank())
                .account(requestDto.getAccount())
                .total_price(requestDto.getTotal_price())
                .delivery_msg(requestDto.getDelivery_msg())
                .build();
        orderRepository.save(order);

        //상품 수량 입력받는 부분
        for (int i = 0; i < requestDto.getOrderRequestDtoList().size(); i++) {
            Item item = itemRepository.findItemById(requestDto.getOrderRequestDtoList().get(i).getItemId());
            int count = requestDto.getOrderRequestDtoList().get(i).getCount();
            OrderDetail orderDetail = OrderDetail.builder()
                    .orders(order)
                    .item(item)
                    .count(count)
                    .build();
            orderDetailRepository.save(orderDetail);
        }

        //추가질문 답변 입력받는 부분
        for (int i = 0; i < requestDto.getOrderAnswerDtoList().size(); i++) {
            OrderQuestion orderQuestion = orderQuestionRepository.findByQuestion_Id(requestDto.getOrderAnswerDtoList().get(i).getQuestionId());
            String answer = requestDto.getOrderAnswerDtoList().get(i).getAnswer();
            OrderAnswer orderAnswer = OrderAnswer.builder()
                    .orders(order)
                    .orderQuestion(orderQuestion)
                    .answer(answer)
                    .build();
            orderAnswerRepository.save(orderAnswer);
        }

        //참여인원 업데이트
        projectRepository.updateParticipantNumber(project_id);

        return new ResponseEntity(HttpStatus.OK);
    }

}