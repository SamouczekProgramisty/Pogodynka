class pogodynka::node_datavault {
  include apt

  include pogodynka::misc
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
