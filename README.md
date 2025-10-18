# Spring Boot K8s Cache Webhook Coordinator

A  Spring Boot application that coordinates **Caffeine cache invalidation** across multiple **Kubernetes pods** using **webhook-based communication**.

### Key Features
- Instantly clears in-memory caches across 50+ pods with one API call
- Built on Spring Boot 3.x and Caffeine Cache
- Uses the Kubernetes API for automatic pod discovery
- Designed for blue-green deployments
- Simple `/clearCache` endpoint → cluster-wide consistency

---

### Quickstart

Below "npm run kubectl:local-clean-redeploy-open-ui" does the following:
- 1. Remove existing OpenTelemetry Jaeger, OTEL Collector, This Spring Boot Kubernetes Pods
- 2. Removes the docker image of this Spring Boot App
- 3. Does mvn clean instal of this app
- 4. Creates Docker Image of this app
- 5. Initializes through "k8s apply" command OTEL Jaeger, OTEL Collector
- 6. Deploys this Spring Boot application as 5 instances of Kubernetes pods
- 7. Open Up the Jaeger UI in the browser
- 8. Open Up the HTTP Actuator Health Check URL of this Spring Boot App

```bash

npm run kubectl:local-clean-redeploy-open-ui

# This invokes the Clear Cache API endpoint - which internally obtains the Kubernetes Pod IPs of this service's other replications
# and invokes "/cache-manager/v1/clearCacheWebHook" WebHook in the other replicas
npm run app:apis:clearCache
```

## Open Telemetry (Jaeger UI)
When the above commands open the Jaeger UI you will find the Traces, Spans of this application in the Jaeger Application
