package com.qualitas.controller.services;

import asjava.uniclientlibs.UniConnectionException;
import asjava.uniobjects.UniCommandException;
import asjava.uniobjects.UniFileException;
import asjava.uniobjects.UniSelectListException;
import asjava.uniobjects.UniSessionException;

import com.qualitas.modelo.ConectaUniverse;
import com.qualitas.entities.RegistroUV;
import com.qualitas.entities.TipoEndoso;

import java.util.ArrayList;
import java.util.List;

public class ObtieneInfo {
  public List<TipoEndoso> traeInfoTipoEndoso() throws UniSessionException, UniConnectionException, 
          UniCommandException, UniSelectListException, UniFileException {
    ConectaUniverse cnn;
    
    List<RegistroUV> regs;
    List<TipoEndoso> lista;
    
    TipoEndoso tEnd;
    
    lista = new ArrayList();
    cnn = new ConectaUniverse();
    regs = cnn.leer("TENDOSO");
    for (RegistroUV reg : regs) {
      tEnd = new TipoEndoso(reg.getId(),reg.getRegistro().extract(1).toString(),reg.getRegistro().extract(2).toString());
      
      lista.add(tEnd);
    }
    
    return lista;
  }
}
