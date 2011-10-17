package edu.sjsu.carbonated.server;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="request")
public class Request{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_id")
	private Long jobId;

	@Column(name="request_body")
	private String requestBody;

	@Column(name="request_status")
	private int requestStatus;

	@Column(name="request_type")
	private int requestType;

    public Request() {
    }

	public Long getJobId() {
		return this.jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getRequestBody() {
		return this.requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public int getRequestStatus() {
		return this.requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public int getRequestType() {
		return this.requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return "Request [jobId=" + jobId + ", requestBody=" + requestBody
				+ ", requestStatus=" + requestStatus + ", requestType="
				+ requestType + "]";
	}
	
	

}