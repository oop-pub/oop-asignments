package Videos;

public class Message {
    public static StringBuilder mesaj = new StringBuilder();

    public Message(StringBuilder mesaj) {
        Message.mesaj = mesaj;
    }

    public StringBuilder getMesaj() {
        return mesaj;
    }

    public void setMesaj(StringBuilder mesaj) {
        Message.mesaj = mesaj;
    }
}
