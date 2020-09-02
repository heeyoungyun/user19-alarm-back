package local;

import local.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    private AlarmRepository alarmRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCompleted_Notify(@Payload ReservationCompleted reservationCompleted){

        if(reservationCompleted.isMe()){
            System.out.println("##### listener Notify : " + reservationCompleted.toJson());

            addNotificationHistory("(Customer)" + reservationCompleted.getCustNm(), "reservationCompleted");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationChanged_Notify(@Payload ReservationChanged reservationChanged){

        if(reservationChanged.isMe()){
            System.out.println("##### listener Notify : " + reservationChanged.toJson());

            addNotificationHistory("(Customer)" + reservationChanged.getCustNm(), "reservationChanged");
        }
    }

    private void addNotificationHistory(String receiver, String message) {
        Alarm history = new Alarm();
        history.setReceiver(String.valueOf(receiver));
        history.setMessage(message);
        alarmRepository.save(history);
    }

}
