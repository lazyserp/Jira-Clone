package com.wissen.Jira.exceptions;

public class ProjectNotFoundException extends RuntimeException
{
    public ProjectNotFoundException(String message)
    {
        super(message);
    }
    
}
