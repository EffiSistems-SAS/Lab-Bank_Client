package Request;

public class RequestAtm {

    private long dineroActual;

    private boolean disponible;

    public RequestAtm(long value, boolean disp) {
        dineroActual = value;
        disponible = disp;
    }

}
