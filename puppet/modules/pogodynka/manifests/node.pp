class pogodynka::node {
  include apt

  include pogodynka::java
  include pogodynka::database
  include pogodynka::firewall
  include pogodynka::tomcat
  include pogodynka::nginx

  package {
    'ntp':
      ensure => 'latest';
  }
}
