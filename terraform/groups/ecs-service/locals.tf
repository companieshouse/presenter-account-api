# Define all hardcoded local variable and local variables looked up from data resources
locals {
  stack_name                = "identity" # this must match the stack name the service deploys into
  name_prefix               = "${local.stack_name}-${var.environment}"
  service_name              = "presenter-account-api"
  container_port            = "3000" # default port required here until prod docker container is built allowing port change via env var
  docker_repo               = "presenter-account-api"
  lb_listener_rule_priority = 15
  lb_listener_paths         = ["/presenter-account/*"]
  healthcheck_path          = "/presenter-account/healthcheck" #healthcheck path for presenter-account-api
  healthcheck_matcher       = "200"
  application_subnet_ids    = data.aws_subnets.application.ids

  # Enable Eric
  use_eric_reverse_proxy  = true
  eric_port               = "3001" # container port plus 1
  eric_version            = "latest"

  stack_secrets              = jsondecode(data.vault_generic_secret.stack_secrets.data_json)
  application_subnet_pattern = local.stack_secrets["application_subnet_pattern"]

  kms_alias       = "alias/${var.aws_profile}/environment-services-kms"
  service_secrets = jsondecode(data.vault_generic_secret.service_secrets.data_json)

  parameter_store_secrets = {
    "vpc_name"             = local.service_secrets["vpc_name"]
    "chs_api_key"          = local.service_secrets["chs_api_key"]
    "internal_api_url"     = local.service_secrets["internal_api_url"]
    "mongodb_url"          = local.service_secrets["mongodb_url"]
    "aes256_key"           = local.service_secrets["aes256_key"]
    "api_key"              = local.service_secrets["api_key"]
    "cache_url"            = local.service_secrets["cache_url"]
  }

  vpc_name             = local.service_secrets["vpc_name"]
  chs_api_key          = local.service_secrets["chs_api_key"]
  internal_api_url     = local.service_secrets["internal_api_url"]
  mongodb_url          = local.service_secrets["mongodb_url"]
  aes256_key           = local.service_secrets["aes256_key"]
  api_key              = local.service_secrets["api_key"]
  cache_url            = local.service_secrets["cache_url"]

  # create a map of secret name => secret arn to pass into ecs service module
  # using the trimprefix function to remove the prefixed path from the secret name
  secrets_arn_map = {
    for sec in data.aws_ssm_parameter.secret :
    trimprefix(sec.name, "/${local.name_prefix}/") => sec.arn
  }

  service_secrets_arn_map = {
    for sec in module.secrets.secrets :
    trimprefix(sec.name, "/${local.service_name}-${var.environment}/") => sec.arn
  }

  task_secrets = [
    { "name" : "CHS_API_KEY", "valueFrom" : "${local.service_secrets_arn_map.chs_api_key}" },
    { "name" : "INTERNAL_API_URL", "valueFrom" : "${local.service_secrets_arn_map.internal_api_url}" },
    { "name" : "MONGODB_URL", "valueFrom" : "${local.service_secrets_arn_map.mongodb_url}" }
  ]

  task_environment = [
    { "name" : "API_URL", "value" : "${var.api_url}" },
    { "name" : "HUMAN_LOG", "value" : "${var.human_log}" },
    { "name" : "LOG_LEVEL", "value" : "${var.log_level}" }
  ]

  eric_secrets = [
    { "name" : "AES256_KEY" , "valueFrom" : "${local.service_secrets_arn_map.aes256_key}" },
    { "name" : "API_KEY" , "valueFrom" : "${local.service_secrets_arn_map.api_key}" },
    { "name" : "CACHE_URL" , "valueFrom" : "${local.service_secrets_arn_map.cache_url}" }
  ]

  eric_environment = [
    { "name": "LOGLEVEL", "value": "${var.log_level}" },
    { "name": "MODE", "value": "api" },
    { "name": "PRESENTER_ACCOUNT_URL", "value" : "${var.presenter_account_url}" },
    { "name": "DEVELOPER_HUB_URL", "value" : "${var.developer_hub_url}" }
  ]
}
