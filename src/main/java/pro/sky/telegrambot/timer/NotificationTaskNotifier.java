package pro.sky.telegrambot.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.TelegramBotService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskNotifier {

    private final NotificationTaskRepository notificationTaskRepository;
    private final TelegramBotService telegramBotService;

    public NotificationTaskNotifier(NotificationTaskRepository notificationTaskRepository, TelegramBotService telegramBotService) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBotService = telegramBotService;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 1)
    @Transactional
    public void task() {
        notificationTaskRepository.findAllByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    telegramBotService.sendMessage(
                            notificationTask.getChatId(), notificationTask.getText()
                    );
                    notificationTaskRepository.delete(notificationTask);
                });
    }

}
