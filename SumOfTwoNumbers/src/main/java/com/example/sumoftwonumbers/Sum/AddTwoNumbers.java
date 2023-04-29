package com.example.sumoftwonumbers.Sum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddTwoNumbers {

    public double sum(double x, double y){
        return x + y;
    }
}
