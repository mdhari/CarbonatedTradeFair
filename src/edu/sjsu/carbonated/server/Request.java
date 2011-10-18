package edu.sjsu.carbonated.server;


import javax.persistence.*;


@Entity
@Table(name="request")
public class Request{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_id")
	private int jobId;

	@Column(name="request_body")
	private String requestBody;

	@Column(name="request_status")
	private int requestStatus;

	@Column(name="request_type")
	private int requestType;
	
	@Column(name="result")
	private String result;

    public Request() {
    }

	public int getJobId() {
		return this.jobId;
	}

	public void setJobId(int jobId) {
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

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "Request [jobId=" + jobId + ", requestBody=" + requestBody
				+ ", requestStatus=" + requestStatus + ", requestType="
				+ requestType + ", result=" + result + "]";
	}
	
	

}