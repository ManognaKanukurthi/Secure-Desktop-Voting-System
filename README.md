<h1 style="color:black;">🗳️ Secure Desktop Voting System</h1>

A secure, object-oriented desktop voting application developed in **Java** with a **Swing-based GUI**. The system ensures safe and reliable elections by integrating with a **MySQL database** for data persistence and integrity, covering everything from voter registration to result declaration.  

---

<h2 style="color:black;">✨ Features</h2>  

<h3 style="color:black;">🔹 Voter Features</h3>  
<li><b>Secure Registration:</b> Register with a unique Voter ID and password.</li> 
<li><b>Login Authentication:</b> Secure login for registered voters. </li>
<li><b>Candidate Viewing:</b> Browse the list of all candidates before voting.</li>
<li><b>One-Time Voting:</b> Prevents duplicate votes by ensuring each voter can vote only once. </li>
<li><b>Vote Confirmation:</b> Users confirm their choice before submission.  </li>

<h3 style="color:black;">🔹 Admin Features</h3>  
<li><b>Secure Admin Login: </b>Separate hardcoded credentials for administrators.</li>  
<li><b>Candidate Management:</b> Add new candidates to the ballot.</li>  
<li><b>Live Vote Monitoring:</b> View real-time vote counts. </li> 
<li><b>Voter List Display:</b> See all registered voters. </li> 
<li><b>Result Declaration:</b> Declare results and display the winner.</li>  

---

<h2 style="color:black;">🛠️ Tech Stack</h2>  
<li><b>Backend:</b> Java </li> 
<li><b>Frontend (GUI):</b> Java Swing </li> 
<li><b>Database:</b> MySQL</li>  
<li><b>Connector: </b>MySQL Connector/J (JDBC Driver) </li> 

---

<h2 style="color:black;">📋 Prerequisites</h2>  
Before running the application, ensure you have installed:  
<li>Java Development Kit (JDK): Version 8 or higher</li>  
<li>MySQL Server </li>
<li>MySQL Workbench (or any SQL client)  </li>

---

<h2 style="color:black;">⚙️ Setup and Installation</h2>  

<h3 style="color:black;">1. Database Setup</h3>  
Run the following SQL script in MySQL:  

```sql
-- Create a new database
CREATE DATABASE voting_system;

-- Switch to the new database
USE voting_system;

-- Create the 'voters' table
CREATE TABLE voters (
    voter_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password_hash INT NOT NULL
);

-- Create the 'candidates' table
CREATE TABLE candidates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    party VARCHAR(255) NOT NULL
);

-- Create the 'votes' table
CREATE TABLE votes (
    voter_id VARCHAR(255) PRIMARY KEY,
    candidate_id INT NOT NULL,
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(id)
);

````

<h3 style="color:black;">2) Configure Database Connection</h3>
<p>Edit <code>DatabaseService.java</code> and update your MySQL credentials:</p>

```java
private static final String USER = "your_username";  // e.g., "root"
private static final String PASSWORD = "your_password";
```

<h3 style="color:black;">3) Add MySQL JDBC Driver</h3>
<p>Download the <b>MySQL Connector/J</b> <code>.jar</code> file and place it in your project folder (root directory).</p>

<hr/>

<h2 style="color:black;">▶️ Running the Application</h2>

<p><b>1.</b> Open a terminal in your project’s root directory.</p>

<p><b>2.</b> Compile all Java files:</p>

```bash
javac *.java
```

<p><b>3.</b> Run the GUI application with the MySQL connector in the classpath:</p>

<p><b>Windows</b></p>

```bash
java -cp ".;mysql-connector-j-9.4.0.jar" GuiApp
```

<p><b>macOS / Linux</b></p>

```bash
java -cp ".:mysql-connector-j-9.4.0.jar" GuiApp
```

<p><b>4.</b> The <b>Secure Voting System GUI</b> will appear on your screen.</p>

<hr/>

<h2 style="color:black;">📌 Notes</h2>
<ul>
  <li>Ensure MySQL server is running before launching the application.</li>
  <li>Update database credentials in <code>DatabaseService.java</code> as per your setup.</li>
  <li>Use <b>hardcoded admin credentials</b> (as defined in your code) to access admin functionalities.</li>
</ul>
