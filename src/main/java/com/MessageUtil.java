package com;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import java.util.Locale;

/*.........................Internationalisation.............................*/
@Service
public class MessageUtil implements MessageSourceAware {
    MessageSource messageSource;

    public String getMessage(String msg){
        return messageSource.getMessage(msg,null, Locale.US);
    }
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource=messageSource;
    }
}
