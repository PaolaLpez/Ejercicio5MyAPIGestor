variable "aws_region" {
  description = "Región de AWS para el despliegue"
  type        = string
  default     = "us-east-1"
}

variable "environment" {
  description = "Ambiente de ejecución"
  type        = string
  default     = "production-devops"
}

variable "instance_type" {
  description = "Tipo de instancia EC2"
  type        = string
  default     = "t3.medium"
}

variable "ami_id" {
  description = "AMI Ubuntu 22.04 LTS en us-east-1"
  type        = string
  default     = "ami-0c7217cdde317cfec"
}

variable "ssh_key_name" {
  description = "Nombre del KeyPair SSH en AWS"
  type        = string
  default     = "devops-admin-proyectos"
}
