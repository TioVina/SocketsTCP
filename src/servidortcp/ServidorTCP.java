package servidortcp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

public class ServidorTCP {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        geraSocketServidor();
        recebeProcessaRequisicao();
    }

    static ServerSocket servidor;
    static int numeroSocket = 2230;

    private static void criarDiretórioEnvioJSON() {
        File diretorio = new File(System.getProperty("user.home") + "\\Documents\\EnvioJSON");
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public static void geraSocketServidor() {
        try {
            servidor = new ServerSocket(numeroSocket);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getDate() {
        return java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private static String getTime() {
        return java.time.LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
    }

    public static void recebeProcessaRequisicao() throws IOException, ClassNotFoundException {

        while (true) {

            System.out.println("Esperando alguém se conectar...");
            Socket cliente = servidor.accept();

            try {

                ObjectInputStream recebimento = new ObjectInputStream(cliente.getInputStream());

                System.out.println("Cliente conectado.");

                System.out.println("Requisição recebida de: " + cliente.getInetAddress().getHostAddress());

                String dadosConvertidos = recebimento.readObject() + "";

                if (dadosConvertidos.equals("Pls Create New Socket")) {
                    numeroSocket = numeroSocket + 1;
                    cliente.close();
                    servidor.close();
                    geraSocketServidor();

                } else {

                    if (dadosConvertidos.equals("get_date")
                            || dadosConvertidos.equals("get_time")) {

                        JSONObject json = new JSONObject();

                        if (dadosConvertidos.equals("get_date")) {
                            json.put("date", getDate());
                        } else {
                            json.put("time", getTime());
                        }

                        criarDiretórioEnvioJSON();

                        File arquivo = new File(System.getProperty("user.home")
                                + "\\Documents\\EnvioJSON\\EnvioJsonServ.json");

                        FileWriter escreveArquivo = new FileWriter(arquivo.getAbsolutePath());
                        escreveArquivo.write(json.toJSONString());
                        System.out.println(json.toJSONString());
                        escreveArquivo.close();

                        byte[] arrayArquivo = json.toJSONString().getBytes();

                        OutputStream envio = cliente.getOutputStream();
                        envio.write(arrayArquivo, 0, arrayArquivo.length);
                        envio.close();
                    }
                }
                System.out.println(dadosConvertidos);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Cliente desconectado ou com erro no arquivo.");
            }
        }
    }
}
