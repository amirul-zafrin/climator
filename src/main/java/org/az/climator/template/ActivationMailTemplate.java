package org.az.climator.template;

import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.CheckedTemplate;

@CheckedTemplate
public class ActivationMailTemplate {
//    TODO: Add welcome, reset password mail template
//    TODO: Edit template
    public static native MailTemplate.MailTemplateInstance activation(String userName, String activationLink);
}
