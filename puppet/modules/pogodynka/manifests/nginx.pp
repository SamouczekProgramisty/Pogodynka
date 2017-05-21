class pogodynka::nginx {
  class {
    '::nginx':
  }->
  file {
    '/etc/nginx/sites-enables/pogodynka.pietraszek.pl':
      source => 'puppet:///modules/pogodynka/nginx.conf',
      notify => Service['nginx'];
  }
}
