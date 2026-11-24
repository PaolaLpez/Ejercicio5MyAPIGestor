output "public_ip" {
  description = "IP pública asignada al servidor en Cloud"
  value       = aws_instance.devops_server.public_ip
}

output "frontend_url" {
  description = "URL de acceso al Frontend de AdminProyectos"
  value       = "http://${aws_instance.devops_server.public_ip}:8080"
}

output "backend_api_url" {
  description = "URL de acceso al Backend REST API"
  value       = "http://${aws_instance.devops_server.public_ip}:8081"
}

output "netdata_dashboard_url" {
  description = "URL del Dashboard de Monitoreo Netdata"
  value       = "http://${aws_instance.devops_server.public_ip}:19999"
}
