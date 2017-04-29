class pogodynka::java {
  $package = "oracle-java8-installer"
  $responsefile = "/var/cache/debconf/${package}.preseed"

  file {
    'java-apt-list':
      path    => '/etc/apt/sources.list.d/webupd8team-java.list',
      content => "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main\ndeb-src http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main";

    $responsefile:
      ensure  => 'present',
      content => "${package} shared/accepted-oracle-license-v1-1 select true",
  }
  
  package {
    'oracle-java8-installer':
      ensure       => 'latest',
      responsefile => $responsefile,
      require      => [File['java-apt-list'], Class['apt::update']];

    'oracle-java8-set-default':
      ensure  => 'latest',
      require => Package['oracle-java8-installer'];
  }
}
