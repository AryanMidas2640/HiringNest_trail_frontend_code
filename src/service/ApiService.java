package service;

import util.Session;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.io.*;
import java.net.*;
import java.nio.file.Files;


    public class ApiService {

        private static final String BASE_URL = "https://personalpro-2bb3.onrender.com";
        private static final String second_URL= "http://localhost:9090";        // ======================================


    // LOGIN (ONLY username + password)
    // ======================================
    public static String getActiveUsers() {
        return getRequest(second_URL +"/api/jobs/active-users");
    }


    public static String login(
            String username,
            String password) {

        try {

            URL url = new URL(
                    second_URL +"/api/jobs/login"
            );

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            con.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            String json =
                    "{"
                            + "\"username\":\"" + username + "\","
                            + "\"password\":\"" + password + "\""
                            + "}";

            OutputStream os = con.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            String response = readResponse(con);

            // ========================
            // TOKEN EXTRACT
            // ========================
            String token = getValue(response,"token");

            Session.token = token;

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR LOGIN";
        }
    }

    private static String getValue(
            String text,
            String key) {

        try {

            int start =
                    text.indexOf("\"" + key + "\":\"");

            if(start != -1){

                start += key.length() + 4;

                int end =
                        text.indexOf("\"", start);

                return text.substring(start,end);
            }

        } catch(Exception e){}

        return "";
    }



    // ======================================
    // SIGNUP (username + password + role)
    // ======================================
    public static String signup(
            String username,
            String password,
            String role,
            String email   // 🔥 NEW ADD
    ) {

        try {

            URL url =
                    new URL(second_URL + "/api/jobs/signing");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            con.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            // 🔥 FIXED JSON (email added)
            String json =
                    "{"
                            + "\"username\":\"" + username + "\","
                            + "\"password\":\"" + password + "\","
                            + "\"role\":\"" + role + "\","
                            + "\"email\":\"" + email + "\""
                            + "}";

            OutputStream os = con.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            return readResponse(con);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR SIGNUP";
        }
    }

    public static String uploadResume(
            File file,
            String skills,
            String education
    ) {

        try {

            HttpURLConnection con =
                    (HttpURLConnection) new URL(
                            second_URL +"/api/resume/parse"
                    ).openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.token
            );

            String boundary =
                    "----Boundary" + System.currentTimeMillis();

            con.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=" + boundary
            );

            con.setRequestProperty(
                    "Accept",
                    "application/json"
            );

            OutputStream os = con.getOutputStream();

            PrintWriter writer =
                    new PrintWriter(
                            new OutputStreamWriter(os, "UTF-8"),
                            true
                    );

            // ==========================
            // skills dynamic
            // ==========================
            writer.append("--").append(boundary).append("\r\n");
            writer.append(
                    "Content-Disposition: form-data; name=\"skills\""
            ).append("\r\n\r\n");

            writer.append(skills).append("\r\n");
            writer.flush();

            // ==========================
            // education dynamic
            // ==========================
            writer.append("--").append(boundary).append("\r\n");
            writer.append(
                    "Content-Disposition: form-data; name=\"education\""
            ).append("\r\n\r\n");

            writer.append(education).append("\r\n");
            writer.flush();

            // ==========================
            // file
            // ==========================
            writer.append("--").append(boundary).append("\r\n");

            writer.append(
                    "Content-Disposition: form-data; name=\"file\"; filename=\""
                            + file.getName() + "\""
            ).append("\r\n");

            writer.append(
                    "Content-Type: application/pdf"
            ).append("\r\n\r\n");

            writer.flush();

            Files.copy(file.toPath(), os);
            os.flush();

            writer.append("\r\n");

            writer.append("--")
                    .append(boundary)
                    .append("--")
                    .append("\r\n");

            writer.flush();
            writer.close();

            int code = con.getResponseCode();

            InputStream is =
                    (code >= 400)
                            ? con.getErrorStream()
                            : con.getInputStream();

            if (is == null) {
                return "HTTP ERROR : " + code;
            }

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(is)
                    );

            String line;
            StringBuilder response =
                    new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static String logout() {

        try {

            URL url = new URL(second_URL + "/api/jobs/logout");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.token
            );

            con.setDoOutput(true);

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    con.getInputStream()
                            )
                    );

            String line;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            br.close();

            return sb.toString();

        } catch(Exception e){
            return "ERROR";
        }
    }


    // ======================================
    // GET ALL JOBS
    // ======================================
    public static String getAllJobs() {

        return getRequest(
                second_URL +"/api/jobs/all"
        );
    }

    // ======================================
    // GET JOBS BY TENANT
    // ======================================
    public static String getJobsByTenant(
            String tenantId) {

        return getRequest(
                second_URL +"/api/jobs/tenant/"
                        + tenantId
        );
    }

    // ======================================
    // MY POSTED JOBS
    // ======================================
    public static String myPostedJobs() {

        return getRequest(
                second_URL +"/api/jobs/tenant/"
                        + Session.tenantId
        );
    }

    // ======================================
    // SEARCH JOB BY ID
    // ======================================
    public static String getJobById(
            String jobId) {

        return getRequest(
                second_URL +"/api/jobs/"
                        + jobId
        );
    }

    // ======================================
    // APPLY JOB
    // ======================================
    public static String applyJob(String jobId, String status) {

        try {

            URL url = new URL(
                    second_URL +"/api/jobs/apply/"
                            + jobId + "/" + status
            );


            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.token
            );

            return readResponse(con);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR APPLYING";
        }
    }
    // ======================================
    // MY APPLIED JOBS
    // ======================================
    public static String myAppliedJobs() {

        return getRequest(
                second_URL +"/api/jobs/my-applied"
        );
    }

    // ======================================
    // VIEW APPLICANTS
    // ======================================
    public static String myApplicants() {

        return getRequest(
                second_URL +"/api/jobs/my-applicants"
        );
    }

    // ======================================
    // POST JOB
    // ======================================
    public static String postJob(
            String jobId,
            String title,
            String company,
            String city,
            String type,
            String mode,
            String salary,
            String email
    ) {

        try {

            URL url =
                    new URL(
                            second_URL +"/api/jobs/add"
                    );

            HttpURLConnection con =
                    (HttpURLConnection)
                            url.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            con.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            con.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.token
            );

            String json =
                    "{"
                            + "\"jobId\":\"" + jobId + "\","
                            + "\"tenantId\":\"" + Session.tenantId + "\","
                            + "\"jobTitle\":\"" + title + "\","
                            + "\"companyName\":\"" + company + "\","
                            + "\"city\":\"" + city + "\","
                            + "\"jobType\":\"" + type + "\","
                            + "\"workMode\":\"" + mode + "\","
                            + "\"salary\":\"" + salary + "\","
                            + "\"email\":\"" + email + "\""
                            + "}";

            OutputStream os =
                    con.getOutputStream();

            os.write(json.getBytes());
            os.flush();
            os.close();

            return readResponse(con);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR POSTING JOB";
        }
    }

    // ======================================
    // COMMON GET METHOD
    // ======================================
    private static String getRequest(
            String urlPath) {

        try {

            URL url =
                    new URL(urlPath);

            HttpURLConnection con =
                    (HttpURLConnection)
                            url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(
                    "Authorization",
                    "Bearer " + Session.token
            );

            return readResponse(con);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR FETCHING";
        }
    }



        public static String getOnlineUsersCount() {
            return getRequest(
                    second_URL + "/api/jobs/admin/online-users/count"
            );
        }

    // ======================================
    // RESPONSE READER
    // ======================================
    private static String readResponse(
            HttpURLConnection con)
            throws Exception {

        InputStream stream;

        if (con.getResponseCode() >= 200 &&
                con.getResponseCode() < 300) {

            stream = con.getInputStream();

        } else {

            stream = con.getErrorStream();

            if (stream == null) {
                return "SERVER ERROR : "
                        + con.getResponseCode();
            }
        }

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(stream)
                );

        String line;

        StringBuilder response =
                new StringBuilder();

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();

        return response.toString();
    }
        public static String getAllRecruiters() {
            return getRequest(second_URL + "/api/admin/recruiters");
        }

        public static String getStudentCount() {
            return getRequest(
                    second_URL + "/api/admin/student-count"
            );
        }

        public static String getRecruiterCount() {
            return getRequest(
                    second_URL + "/api/admin/recruiter-count"
            );
        }

        public static String getJobCount() {
            return getRequest(
                    second_URL + "/api/admin/job-count"
            );
        }

        public static String getApplicationCount() {
            return getRequest(
                    second_URL + "/api/admin/application-count"
            );
        }

        public static String getAllStudents() {
            return getRequest(
                    second_URL + "/api/admin/students"
            );
        }

}