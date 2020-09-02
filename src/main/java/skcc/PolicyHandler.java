package skcc;

import skcc.config.kafka.KafkaProcessor;
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
    public void wheneverReservationRegistered_Notify(@Payload ReservationRegistered reservationRegistered){

        if(reservationRegistered.isMe()){
            System.out.println("##### listener Notify : " + reservationRegistered.toJson());

            addNotificationHistory("(Customer)" + reservationRegistered.getCustNm(), "reservationRegistered");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCanceled_Notify(@Payload ReservationCanceled reservationCanceled){

        if(reservationCanceled.isMe()){
            System.out.println("##### listener Notify : " + reservationCanceled.toJson());

            addNotificationHistory("(Customer)" + reservationCanceled.getCustNm(), "reservationCanceled");
        }
    }

    private void addNotificationHistory(String receiver, String message) {
        Alarm history = new Alarm();
        history.setReceiver(String.valueOf(receiver));
        history.setMessage(message);
        alarmRepository.save(history);
    }

}
