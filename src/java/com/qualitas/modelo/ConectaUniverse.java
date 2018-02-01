package com.qualitas.modelo;

import asjava.uniclientlibs.UniConnectionException;
import asjava.uniclientlibs.UniDynArray;
import asjava.uniobjects.UniCommand;
import asjava.uniobjects.UniCommandException;
import asjava.uniobjects.UniFile;
import asjava.uniobjects.UniFileException;
import asjava.uniobjects.UniSelectList;
import asjava.uniobjects.UniSelectListException;
import asjava.uniobjects.UniSession;
import asjava.uniobjects.UniSessionException;
import com.qualitas.entities.TipoEndoso;
import com.qualitas.entities.RegistroUV;
import java.util.ArrayList;
import java.util.List;

public class ConectaUniverse {
    private String server = "110.10.0.176"; // ip server
    private String userName = "jemartin";    // usuario
    private String password = "jemartin";   // contraseña
    private String accountPath = "sise";    // cuenta
    public String DBtype= "UNIVERSE";       // tipo de cuenta
    
    private static UniSession session;              // UniVerse session
    
    
    public ConectaUniverse(){
        session = new UniSession();
        procesar();
    }
    
   public List<RegistroUV> leer(String nomBDTable)  throws UniConnectionException,
          UniSessionException, UniCommandException, UniSelectListException, UniFileException {
        UniSelectList uvslLista;
        UniFile uvfBDSise;
        UniDynArray uvdRegBD;
        RegistroUV registroUV;
        String cmdString, idBD;
        UniCommand command = null;
        
        System.out.println("\nEjecuta Comando inicio >>>\n");
        
        List <RegistroUV> lista = new ArrayList<>();
        
        if (!validaConexion()) {
            procesar();
        }
        
        
            cmdString = "SELECT " + nomBDTable;
            command = session.command();            
            command.setCommand(cmdString);
            command.exec();
            
            uvslLista = session.selectList(0);
            uvfBDSise = session.openFile(nomBDTable);
            System.out.println("Entro a la base de datos " + nomBDTable);

            while (!uvslLista.isLastRecordRead()) {
              idBD = uvslLista.next().toString();

              if (!idBD.equals("")) {
                System.out.println("Encontro registro " + idBD);
                uvfBDSise.setRecordID(idBD);
                uvdRegBD = new UniDynArray(uvfBDSise.read().toString());
                registroUV = new RegistroUV(idBD, uvdRegBD);
                lista.add(registroUV);
             }
            }
            
            
        
        System.out.println("Ejecuta Comando fin <<<\n"); 
        return lista;
    }
    
  public static boolean validaConexion() {
    return session.isActive();
  }
  
    public void procesar(){
        try{
            session.setHostName(server);
            session.setUserName(userName);
            session.setPassword(password);
            session.setAccountPath(accountPath);
            session.setDataSourceType(DBtype);			
            System.out.println("Termino asignar valores");

            session.connect();
            System.out.println("Conexion establecida");
            
        } catch (UniSessionException e) {
            System.out.println("Error en la conexion ");
            System.out.println(e.getMessage());
        }catch (Exception e){        
            System.out.println("Error en la conexion " + e.getMessage());
        }
    }
    
}
