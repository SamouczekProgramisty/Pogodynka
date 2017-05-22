class pogodynka::node_thermometer(
  $datavault_endpoint = $::pogodynka::params::datavault_endpoint,
  $healthcheck_endpoint = $::pogodynka::params::healthcheck_endpoint,
  $thermometer_version = $::pogodynka::params::thermometer_version,
  $code_dir = $::pogodynka::params::code_dir,
  $thermometer_input_file_path = $::pogodynka::params::thermometer_input_file_path
) inherits ::pogodynka::params {
  include apt

  include pogodynka::misc
  include pogodynka::java

  package {
    'ntp':
      ensure => 'latest';
  }

  $jar_file = "${code_dir}/thermometer/build/libs/thermometer-${thermometer_version}.jar"
  $authorisation_token = hiera('pogodynka_authorisation_token')

  exec {
    'build_jar':
      command => "${code_dir}/gradlew -b ${code_dir}/thermometer/thermometer.gradle jar",
      creates => $war_file;
  }

  cron {
    'measure_temperature':
      command => "java -jar ${jar_file} ${authorisation_token} ${datavault_endpoint} ${thermometer_input_file_path} && curl -fsS --retry 3 ${healthcheck_endpoint}",
      minute  => '*/10',
      user    => 'pogodynka',
      require => [User['pogodynka'], Exec['build_jar']];

    'remove_old_logs':
      command => 'find /var/log/pogodynka/*.log -mtime +7 -exec rm {} \;',
      hour    => '0',
      user    => 'pogodynka',
      minute  => '1',
      require => User['pogodynka'];
  }
}
