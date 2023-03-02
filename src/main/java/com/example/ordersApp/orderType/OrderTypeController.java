package com.example.ordersApp.orderType;

import com.example.ordersApp.apiError.ApiError;
import com.example.ordersApp.security.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController()
public class OrderTypeController {

    @Autowired
    private OrderTypeServiceImpl mOrderTypeService;

    @Value("${app.secret-token}")
    private String secret = "";

    @PostMapping("/orders/type")
    public ResponseEntity<Object> createNewOrderType(@RequestBody OrderTypeEntity orderType, HttpServletRequest request) {
        if (compareId(orderType.getUserId(), request)) {
            if (mOrderTypeService.createNewOrderType(orderType)) {
                return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.CREATED);
            }
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping("/orders/type/{userId}")
    public ResponseEntity<Object> getAllOrderTypesByUserId(@PathVariable Long userId, HttpServletRequest request) {
        if (compareId(userId, request)) {
            return new ResponseEntity<Object>(mOrderTypeService.getAllOrderTypesByUserId(userId), new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());

    }

    @GetMapping("/orders/type/{userId}/{orderTypeId}")
    public ResponseEntity<Object> getOrderTypeEntity(@PathVariable Long userId, @PathVariable Long orderTypeId, HttpServletRequest request) {
        if (compareId(userId, request) && mOrderTypeService.getOrderTypeEntity(orderTypeId, userId) != null) {
            return new ResponseEntity<Object>(mOrderTypeService.getOrderTypeEntity(orderTypeId, userId), new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @DeleteMapping("/orders/type/{userId}/{orderTypeId}")
    public ResponseEntity<Object> deleteOrderType(@PathVariable Long userId, @PathVariable Long orderTypeId, HttpServletRequest request) {
        if (compareId(userId, request)) {
            if (mOrderTypeService.deleteOrderType(orderTypeId, userId)) {
                return new ResponseEntity<Object>(true, new HttpHeaders(), HttpStatus.OK);
            }
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    public boolean compareId(Long userId, HttpServletRequest request) {
        Long id = Long.parseLong(AuthHandlerInterceptor.decodeToken(request, secret).getIssuer());
        return Objects.equals(userId, id);
    }

}
