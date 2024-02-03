package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler({LoginFailedException.class,IdNotFoundException.class})
        public ResponseEntity<String> UnauthorizedHandler(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }


    @ExceptionHandler({CouponNotExistException.class})
    public ResponseEntity<String> NotFoundHandler(CouponNotExistException n){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(n.getMessage());
    }

    @ExceptionHandler({CouponDateException.class,CouponAmountException.class,CouponPriceException.class
    ,CouponAlreadyOwnedException.class,CouponTitleAlreadyExistsException.class,NameExistsException.class
    , CouponDescriptionException.class})
    public ResponseEntity<String> BadRequestHandler(Exception ex){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

//    @ExceptionHandler({})
//    public ResponseEntity<String> CouponExceptionHandler(LoginFailedException l){
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(l.getMessage());
//    }

//    @ExceptionHandler({CouponAmountException.class})
//    public ResponseEntity<String> HandleCouponAmountEx(CouponAmountException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({CouponPriceException.class})
//    public ResponseEntity<String> HandleCouponPriceEx(CouponPriceException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({CouponAlreadyOwnedException.class})
//    public ResponseEntity<String> HandleCouponAlreadyOwnedEx(CouponAlreadyOwnedException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({CouponTitleAlreadyExistsException.class})
//    public ResponseEntity<String> HandleCouponTitleAlreadyExistsEx(CouponTitleAlreadyExistsException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({IdNotFoundException.class})
//    public ResponseEntity<String> HandleIdNotFoundEx(IdNotFoundException exception){
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({NameExistsException.class})
//    public ResponseEntity<String> HandleNameExistsEx(NameExistsException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }

//    @ExceptionHandler({EmailExistsException.class})
//    public ResponseEntity<String> HandleEmailExistsEx(EmailExistsException exception){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(exception.getMessage());
//    }
}
