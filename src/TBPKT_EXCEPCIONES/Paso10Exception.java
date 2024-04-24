package TBPKT_EXCEPCIONES;

/**
 * Esta excepción sirve para indicar que se presento un problema procesando el paso 10
 */
public class Paso10Exception extends Exception{
    private int registrosCargados;

    public Paso10Exception(String causa, int consecutivo ) {
        super( causa );
        registrosCargados = consecutivo;
    }
    public int darRegistrosCargados( ) {
        return registrosCargados;
    }
}
