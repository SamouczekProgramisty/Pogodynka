class pogodynka::node_thermometer(
  $datavault_endpoint = $::pogodynka::params::datavault_endpoint,
  $healthcheck_endpoint = $::pogodynka::params::healthcheck_endpoint,
  $thermometer_version = $::pogodynka::params::thermometer_version,
  $code_dir = $::pogodynka::params::code_dir
) inherits ::pogodynka::params {
  include apt

  include pogodynka::misc
  include pogodynka::java

  package {
    'ntp':
      ensure => 'latest';
  }

  $jar_file = "${code_dir}/thermometer/build/libs/thermometer${thermometer_version}.jar"

  exec {
    'build_jar':
      command => "${code_dir}/gradlew -b ${code_dir}/thermometer/thermometer.gradle jar",
      creates => $war_file;
  }

  cron {
    'measure_temperature':
      command => "java -jar ${jar_file} ${hiera('pogodynka_authorisation_token')} ${datavault_endpoint} && curl -fsS --retry 3 ${healthcheck_endpoint}",
      minute  => '*/10',
      user    => 'pogodynka',
      require => [User['pogodynka'], Exec['build_jar']]

    'remove old logs':
      command => "find /var/log/pogodynka/*.log -mtime +7 -exec rm {} \;"
      hour    => '0',
      user    => 'pogodynka',
      minute  => '1',
      require => User['pogodynka']
  }
}
