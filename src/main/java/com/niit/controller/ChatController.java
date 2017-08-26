package com.niit.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.msg.OutputMessage;

import oracle.jdbc.driver.Message;




@Controller
@RequestMapping("/")
public class ChatController 
{
 
  @MessageMapping("/chat")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(Message message) 
  {
    System.out.println("Message sent");
  return new OutputMessage( (com.niit.msg.Message) message,new Date());
  }
}

