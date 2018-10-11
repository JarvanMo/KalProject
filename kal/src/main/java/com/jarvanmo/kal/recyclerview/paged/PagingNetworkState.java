package com.jarvanmo.kal.recyclerview.paged;

public class PagingNetworkState {
    public final static PagingNetworkState LOADED =new  PagingNetworkState(Status.SUCCESS);
    public final static PagingNetworkState LOADING = new PagingNetworkState(Status.RUNNING);
    public  static PagingNetworkState error(String msg) {
        return new PagingNetworkState(Status.FAILED, msg);
    }

    private Status status;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private PagingNetworkState(Status status){
        this(status,null);
    }


    private PagingNetworkState(Status status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
