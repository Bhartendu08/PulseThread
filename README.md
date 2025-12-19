# PulseThread: Multithreaded Web Server & Telemetry Dashboard

PulseThread is a high-performance, Java-based web server designed to demonstrate the efficiency of **Fixed Thread Pooling** and the **Producer-Consumer** architecture. The project includes a real-time monitoring dashboard that visualizes server health and traffic metrics via a REST-style API.



## 🚀 Key Features
* **Managed Concurrency**: Utilizes a `FixedThreadPool` to maintain a stable memory footprint and prevent resource exhaustion under heavy load.
* **Real-Time Telemetry**: A custom `/stats` endpoint provides server metrics (uptime, request counts, active threads) in JSON format.
* **Dynamic Visualization**: A modern dashboard built with **Chart.js** that visualizes traffic trends and worker thread activity in real-time.
* **Resource Efficiency**: Implements thread recycling to handle thousands of requests with a minimal, static set of worker threads.

## 🛠️ Technical Stack
* **Backend**: Java (Socket Programming, NIO, ExecutorService).
* **Frontend**: HTML5, CSS3 (Grid/Flexbox), JavaScript (Fetch API).
* **Visualization**: Chart.js.
* **Performance Testing**: Apache JMeter.

## 📊 Performance & Stress Test Results
The server was subjected to high-intensity stress testing using **Apache JMeter** to evaluate its stability limits.

* **Total Samples**: 6,000 requests.
* **Throughput**: Maintained a steady **35.2 requests/sec**.
* **Median Latency**: 50% of requests were handled in **1,068 ms**.
* **Reliability**: Successfully managed massive concurrency with only 10 worker threads, proving the effectiveness of the `LinkedBlockingQueue` buffer.



## 📂 Project Structure
* `Server.java`: Core server logic and thread pool management.
* `www/index.html`: Web dashboard for real-time monitoring.
* `www/stats`: API endpoint for telemetry data.

## 🔧 Installation & Usage
### Prerequisites
* Java Development Kit (JDK) 8 or higher.
