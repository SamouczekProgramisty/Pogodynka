class pogodynka::node {
  include apt

  include pogodynka::java
  include pogodynka::database
  include pogodynka::firewall
  include pogodynka::tomcat

  package {
    'ntp':
      ensure => 'latest';
  }
}
