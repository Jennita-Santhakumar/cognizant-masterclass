# Example Terraform config: deploys the image built in ../03-Docker
# to Azure Container Apps. (Conceptually equivalent services exist on
# AWS as ECS/Fargate and on GCP as Cloud Run — swap the provider block
# and resource types to match whichever cloud you're using.)
#
# This is a learning sample, not production-ready: no remote state
# backend, no secrets management beyond a placeholder variable.

terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
  }
}

provider "azurerm" {
  features {}
}

variable "container_image" {
  description = "Full image reference, e.g. ghcr.io/org/user-microservice:latest"
  type        = string
}

resource "azurerm_resource_group" "masterclass" {
  name     = "rg-cognizant-masterclass"
  location = "East US"
}

resource "azurerm_container_app_environment" "masterclass_env" {
  name                = "cae-cognizant-masterclass"
  location            = azurerm_resource_group.masterclass.location
  resource_group_name = azurerm_resource_group.masterclass.name
}

resource "azurerm_container_app" "user_microservice" {
  name                         = "user-microservice"
  container_app_environment_id = azurerm_container_app_environment.masterclass_env.id
  resource_group_name          = azurerm_resource_group.masterclass.name
  revision_mode                = "Single"

  template {
    container {
      name   = "user-microservice"
      image  = var.container_image
      cpu    = 0.5
      memory = "1Gi"
    }

    min_replicas = 1
    max_replicas = 3
  }

  ingress {
    external_enabled = true
    target_port       = 8080
    traffic_weight {
      percentage      = 100
      latest_revision = true
    }
  }
}

output "app_url" {
  value = azurerm_container_app.user_microservice.latest_revision_fqdn
}
