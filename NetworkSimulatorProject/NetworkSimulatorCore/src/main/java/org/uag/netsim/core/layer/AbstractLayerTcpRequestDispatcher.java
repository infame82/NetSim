package org.uag.netsim.core.layer;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class AbstractLayerTcpRequestDispatcher<R extends LayerTcpRequest,T extends LayerTcpResponse>
        implements LayerTcpRequestDispatcher{

    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public AbstractLayerTcpRequestDispatcher(Socket socket) throws IOException{
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            resolveRequest(in.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void resolveRequest(Object obj) {
        if(obj instanceof LayerTcpRequest){
            try {
                sendMsg(resolveRequest((R)obj));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg(Object obj) throws IOException {
        out.writeObject(obj);
        out.flush();
        socket.close();
    }

    protected abstract T resolveRequest(R request);
}
