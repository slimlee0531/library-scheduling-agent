package com.slim.agent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ScheduleAgent {
    // AI Agent for scheduling
    String schedule();
}