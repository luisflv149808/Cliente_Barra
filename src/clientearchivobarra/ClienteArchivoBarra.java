package clientearchivobarra;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteArchivoBarra {

    public static void main(String[] args) {

        if (args.length == 3) {

            if (Metodos.ValidarIp(args[0])) {

                if (Metodos.Numerico(args[1])) {

                    Socket client = null;
                    try {
                        client = new Socket(args[0], Integer.parseInt(args[1]));
                    } catch (IOException ex) {
                        System.out.println("Error al crear el socket " + ex);
                        System.exit(1);
                    }

                    PrintWriter escritor = null;
                    try {
                        escritor = new PrintWriter(client.getOutputStream(), true);
                    } catch (IOException ex) {
                        System.out.println("Error al crear el escritor " + ex);
                        System.exit(2);
                    }

                    BufferedReader lector = null;
                    try {
                        lector = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    } catch (IOException ex) {
                        System.out.println("Error al crear el lector " + ex);
                        System.exit(3);
                    }

                    escritor.println(args[2]);
                    String datosEntrada = "";
                    while (true) {
                        try {
                            datosEntrada = lector.readLine();
                            if (datosEntrada.equals("null")) {
                                client.close();
                            }
                        } catch (Exception e) {
                            System.exit(2);

                            try {
                                client.close();
                            } catch (IOException ex) {
                                System.out.println("Error al cerrar el cliente " + ex);
                                System.exit(3);
                            }
                        }

                        
                        BufferedInputStream bis = null;
                        BufferedOutputStream bos = null;
                        byte[] receivedData;
                        int in;
                        String file = "";
                        
                        receivedData = new byte[1024];
                        try {
                            bis = new BufferedInputStream(client.getInputStream());
                        } catch (IOException ex) {
                            System.out.println("Error al crear el Buffer " + ex);
                            System.exit(3);
                        }

                        DataInputStream dis = null;
                        try {
                            dis = new DataInputStream(client.getInputStream());
                        } catch (IOException ex) {
                            System.out.println("Error al recibir " + ex);
                            System.exit(4);
                        }

                        try {
                           
                            file = dis.readUTF();
                        } catch (IOException ex) {
                            System.out.println("Error al leer " + ex);
                            System.exit(5);
                        }

                        file = file.substring(file.indexOf('\\') + 1, file.length());

                        try {
                          
                            bos = new BufferedOutputStream(new FileOutputStream(file));
                        } catch (FileNotFoundException ex) {
                            System.out.println("Error al crear el Buffer " + ex);
                            System.exit(6);
                        }
                        System.out.println("Descargando Archivo");
                        double i = 0;
                        int repetido = 0;
                        int porcentaje = 0;
                        try {
                            while ((in = bis.read(receivedData)) != -1) {
                                bos.write(receivedData, 0, in);
                                i += in;
                                porcentaje = ((int) ( i / ((Float.parseFloat(datosEntrada))) * 100));
                                if (porcentaje != repetido) {
                                    repetido = porcentaje;

                                    if (porcentaje == 10) {
                                        System.out.print("|*****                                             |" + porcentaje + "% \r");
                                    } else if (porcentaje == 20) {
                                        System.out.print("|*********                                         |" + porcentaje + "% \r");
                                    } else if (porcentaje == 30) {
                                        System.out.print("|**************                                    |" + porcentaje + "% \r");
                                    } else if (porcentaje == 40) {
                                        System.out.print("|*******************                               |" + porcentaje + "% \r");
                                    } else if (porcentaje == 50) {
                                        System.out.print("|************************                          |" + porcentaje + "% \r");
                                    } else if (porcentaje == 60) {
                                        System.out.print("|******************************                    |" + porcentaje + "% \r");
                                    } else if (porcentaje == 70) {
                                        System.out.print("|***********************************               |" + porcentaje + "% \r");
                                    } else if (porcentaje == 80) {
                                        System.out.print("|****************************************          |" + porcentaje + "% \r");
                                    } else if (porcentaje == 90) {
                                        System.out.print("|*********************************************     |" + porcentaje + "% \r");
                                    } else if (porcentaje == 100) {
                                        System.out.println("|**************************************************|" + porcentaje + "% \r");
                                        System.out.println("Envio de archivo completo");
                                    }

                                   
                                }
                            }
                        } catch (IOException ex) {
                           System.out.println("Error al descargar archivoy" + ex);
                            System.exit(9);
                        }
                        try {
                            bos.close();
                        } catch (IOException ex) {
                            System.out.println("Error al cerrar el Buffer " + ex);
                            System.exit(7);
                        }

                        try {
                            dis.close();
                        } catch (IOException ex) {
                            System.out.println("Error al cerrar el DataInput " + ex);
                            System.exit(8);
                        }

                    }

                } else {
                    System.out.println("!!Error!!. El puerto debe ser númerico (Argumento 2)");
                }

            } else {
                System.out.println("Dirección IP Inválida");
                System.exit(0);
            }

        } else {
            System.out.println("!!Error!!. Ingresa todos los argumentos");
        }

        
       
    }

}
