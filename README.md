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

```bash
# Deploy to your cluster
kubectl apply -f k8s/deployment.yaml

# Trigger a global cache refresh
curl -X POST https://my-orders.example.com/clearCache
```

- In your browser, simply open: -> http://localhost:31579/actuator/health
- Spring Actuator (health check): -> http://localhost:31579/ -> {"status":"UP"}
- Inside the cluster →  app listens on port 8080 
- Outside the cluster → it’s exposed on NodePort 31579


## ⚙️ What Each `npm run` Command Does

| **Command** | **Purpose / Description** |
|--------------|----------------------------|
| `npm run build` | Builds the Spring Boot project using Maven (`mvn clean install`). |
| `npm run docker:build` | Builds the Docker image for your Spring Boot app using `dockerBuild/Dockerfile`. |
| `npm run docker:run` | Runs your app container locally on port **8080**. |
| `npm run docker:stop` | Stops the running app container (if any). |
| `npm run docker:rm` | Removes the stopped container. |
| `npm run docker:push` | Pushes the image to your configured container registry. |
| `npm run kubectl:local-deploy` | Deploys your Spring Boot app to local Kubernetes (`devops/k8s/overlays/local`). |
| `npm run kubectl:local-scale-pods` | Scales the app deployment (e.g., to 5 replicas). |
| `npm run kubectl:local-info` | Displays deployments, pods, services, and endpoints. |
| `npm run kubectl:local-svc` | Shows the service details for your app. |
| `npm run kubectl:local-pods` | Lists all pods for the app. |
| `npm run kubectl:local-deployments` | Lists all deployments in the current namespace. |
| `npm run kubectl:local-nodeport` | Opens the NodePort service for local access. |
| `npm run kubectl:local-delete` | Deletes the local Kubernetes deployment (safe cleanup). |
| `npm run kubectl:local-redeploy` | Rebuilds the image, deletes old deployment, and redeploys fresh. |

---

## 🔹 OpenTelemetry (OTEL) & Jaeger Commands

| **Command** | **Purpose / Description** |
|--------------|----------------------------|
| `npm run otel:jaeger:apply` | Deploys **Jaeger** with OTLP (HTTP + gRPC) endpoints and persistence. |
| `npm run otel:jaeger:delete` | Deletes the Jaeger deployment and service. |
| `npm run otel:jaeger:status` | Shows Jaeger pod status in the `observability` namespace. |
| `npm run otel:jaeger:logs` | Streams logs from the Jaeger pod (useful for debugging). |
| `npm run otel:jaeger:ui` | Opens the Jaeger web UI at [http://localhost:31686](http://localhost:31686). |

---

## 🔹 OpenTelemetry Collector (Optional)

| **Command** | **Purpose / Description** |
|--------------|----------------------------|
| `npm run otel:collector:apply` | Deploys the OpenTelemetry Collector (if added later). |
| `npm run otel:collector:delete` | Removes the collector from the cluster. |
| `npm run otel:collector:logs` | Streams the collector logs for debugging pipeline issues. |

---

## 🔹 Verification & Diagnostics

| **Command** | **Purpose / Description** |
|--------------|----------------------------|
| `npm run otel:verify:endpoint` | Tests connectivity to the Jaeger OTLP HTTP endpoint (`http://localhost:30418/v1/traces`). |
| `npm run otel:verify:pods` | Lists all pods running in the `observability` namespace. |
| `npm run otel:verify:services` | Lists all services in the `observability` namespace. |

## Overall

```shell
# Build and Package App
# This compiles your Spring Boot app and builds the Docker image.
npm run build
npm run docker:build

# Clean Up Old Deployments (Optional but Recommended)
# Cleans up any old collector or Jaeger pods so we start fresh.
# (These scripts safely skip if nothing exists.)
npm run otel:collector:delete
npm run otel:jaeger:delete

# Deploy Observability Stack
npm run otel:namespace:apply
npm run otel:deploy:all

# This creates the observability namespace and deploys:
# Jaeger (Collector, Query UI)
# OTEL Collector (receives traces from your app → sends to Jaeger)

# Verify Everything Is Running
# If otel-collector is still crashing, fix your command line in YAML:
# command: ["/otelcontribcol", "--config", "/etc/otel/config.yaml"] 
# and re-apply -> npm run otel:collector:apply
npm run otel:verify:pods

# View Collector Logs
# You should see messages like:
# Connected to Jaeger at jaeger.observability.svc.cluster.local:14268
# Exporting spans...
npm run otel:collector:logs

# Open Jaeger UI
# Then go to your browser at: http://localhost:31686
# You’ll see Jaeger’s web UI.
# This Spring Boot app traces will appear under the service name: spring-k8s-cache-webhook-coordinator
npm run otel:jaeger:ui

# Redeploy After Any Config Change
# Whenever you update:
# otel-collector.yaml
# otel-jaeger.yaml
# application.yml tracing config

npm run otel:delete:all && npm run otel:deploy:all

# Then confirm again:
npm run otel:verify:pods
npm run otel:collector:logs


# Debugging Helpers
# If something still seems off:
kubectl describe pod -n observability -l app=otel-collector
kubectl logs -n observability -l app=otel-collector --previous


```

## Clean Everything And ReDeploy This App Local K8S

```shell
# Clean everything and rebuild app + observability stack
npm run kubectl:local-clean-redeploy
npm run otel:clean-redeploy:all

# Verify pods
npm run otel:verify:pods

# Access app
npm run kubectl:local-nodeport

# Access Jaeger UI
npm run otel:jaeger:ui-k8s

```