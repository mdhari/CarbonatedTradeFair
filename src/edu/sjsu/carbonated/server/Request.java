package edu.sjsu.carbonated.server;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JobRequests")
public class Request implements Serializable {

	private static final long serialVersionUID = 5908209142947649298L;

	@Id
	@GeneratedValue
	private int id;
	
	@Basic
	private String request_type;
	
	@Basic
	private String request_body;
	
	@Basic
	private String request_status;
	
	@Basic
	private String request_result;

	public Request() {
	}

	public Request(String request_type, String request_body, String request_status) {
		this.request_type = request_type;
		this.request_body = request_body;
		this.request_status = request_status;
		
	}
	
	@Override
	public String toString() {
		return "Request [id=" + id + ", request_type=" + request_type
				+ ", request_body=" + request_body + ", request_status="
				+ request_status + ", request_result=" + request_result + "]";
	}

}