package local;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="NotificationHistory_table")
public class Alarm {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String receiver;
    private String message;

    @PostPersist
    public void onPostPersist(){
        AlarmRegistered alarmRegistered = new AlarmRegistered();
        BeanUtils.copyProperties(this, alarmRegistered);
        alarmRegistered.publishAfterCommit();
        System.out.println(" 저장 후 : 메시지 발송 " + alarmRegistered.toJson());


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
