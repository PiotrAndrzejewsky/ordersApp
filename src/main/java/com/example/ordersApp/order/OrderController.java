package com.example.ordersApp.order;

import com.example.ordersApp.apiError.ApiError;
import com.example.ordersApp.security.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl mOrderService;

    @Value("${app.secret-token}")
    private String secret = "";

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody OrderEntity orderEntity, HttpServletRequest request) {
        if (compareId(orderEntity.getUserId(), request)) {
            if (mOrderService.createOrder(orderEntity, Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer()))) {
                return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.CREATED);
            }
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long orderId, HttpServletRequest request) {
        if (mOrderService.deleteOrder(orderId, Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer()))) {
            return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderEntity orderEntity, @PathVariable Long orderId, HttpServletRequest request) {
        if (compareId(orderEntity.getUserId(), request)) {
            mOrderService.updateOrder(orderEntity, orderId);
            return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<Object> changeCompletionStatus(@PathVariable Long orderId, HttpServletRequest request) {
        if (mOrderService.changeCompletionStatus(orderId, Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer()))) {
            return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/orders/week/{userId}/{dateTime}")
    public ResponseEntity<Object> getOrdersByWeek(@PathVariable Long userId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime, HttpServletRequest request) {
        if (compareId(userId, request)) {
            return new ResponseEntity<Object>(mOrderService.getOrdersByWeek(userId, dateTime), new HttpHeaders(), HttpStatus.OK);
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/orders/day/{userId}/{dateTime}")
    public ResponseEntity<Object> getOrdersByDay(@PathVariable Long userId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime, HttpServletRequest request) {
        if (compareId(userId, request)) {
            return new ResponseEntity<Object>(mOrderService.getOrdersByDay(userId, dateTime), new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Object> getOrder(@PathVariable Long orderId, HttpServletRequest request) {
        Long userId = Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer());
        if (mOrderService.getOrder(orderId, userId) != null) {
            return new ResponseEntity<Object>(mOrderService.getOrder(orderId, userId), new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/test/{date}")
    public void test(@PathVariable String date) {
        System.out.println(date);
        LocalDateTime dateTime = LocalDateTime.parse(date);
        System.out.println(dateTime);
    }

    public boolean compareId(Long userId, HttpServletRequest request) {
        Long id = Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer());
        return Objects.equals(userId, id);
    }
}
