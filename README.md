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
