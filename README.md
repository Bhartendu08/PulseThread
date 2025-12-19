PulseThread: Multithreaded Web Server & Telemetry Dashboard

PulseThread is a high-performance Java-based web server designed to demonstrate the efficiency of Fixed Thread Pooling and the Producer-Consumer architecture. It features a real-time monitoring dashboard that visualizes server health and traffic metrics.
🚀 Key Features

    Managed Concurrency: Uses a FixedThreadPool to prevent resource exhaustion and OutOfMemory errors under heavy load.

    Asynchronous Telemetry: A custom REST-style /stats API provides server metrics in JSON format.

    Real-Time Dashboard: A modern frontend built with Chart.js that visualizes traffic spikes and active worker threads.

    Resource Efficiency: Implements thread recycling, ensuring that a static set of worker threads handles thousands of requests.

🛠️ Technical Stack

    Backend: Java (Socket Programming, NIO, ExecutorService).

    Frontend: HTML5, CSS3 (Grid/Flexbox), JavaScript (Fetch API).

    Visualization: Chart.js.

    Testing: Apache JMeter.

📈 Performance Results

During stress testing with Apache JMeter, the server was subjected to a burst of 6,000 requests:

    Throughput: Maintained a steady 35.2 requests/sec.

    Stability: Handled massive concurrency with 10 worker threads while maintaining a stable memory footprint.

    Reliability: Achieved high success rates by utilizing a LinkedBlockingQueue for request buffering.
