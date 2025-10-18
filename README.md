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

curl http://localhost:30001/actuator/health
curl http://localhost:30001/trade-info/crud/get-all-trades
curl http://localhost:30001/trade-info/crud/create-trade
curl http://localhost:30001/trade-info/crud/get-all-trades
curl http://localhost:31686/search # Jaeger UI
# Port number for above Jaeger UI can change based on the Kubernetes deployment in this codebase

# Below delete all the pods in this codebase
npm run kubectl:clean:all
```

## Open Telemetry (Jaeger UI)
When the above commands open the Jaeger UI you will find the Traces, Spans of this application in the Jaeger Application

In Jaeger, if you find below it indicates your API call through "npm run app:apis:clearCache" is invoked
"spring-k8s-cache-webhook-coordinator: http get /cache-manager/v1/clearCache 22c9508"

In Jaeger, if you find below it indicates your above API call internally identified all the Kubernetes Replicas of this Spring Boot Application and invokes  
"http get /cache-manager/v1/clearCacheWebHook" on each of the replica.

The purpose of the above "clearCacheWebHook" is to show case the WebHook real use when an application caches the data and clear cache event when perfoermed by application teams 
should clear cache in all other replicas of this deployment. 

If we use Kafka or Azure EventHub then it will be heavy tech stack (Kafka brokers/servers, Kafka client programming, Topic creeation, publishing topic etc)

The main purpose of this Spring Boot App is WebHook showcase, as part of that it also show cases Kubernetes pods and OpenTelemry capabilities that shoudl be integrated as part of this Spring Boot applicaiton.

Since in devops/k8s/overlays/local/kustomization.yaml file we have 5 pods defined, you should see 5 Traces with the webhook endpoint "/cache-manager/v1/clearCacheWebHook" in Jaeger UI. 