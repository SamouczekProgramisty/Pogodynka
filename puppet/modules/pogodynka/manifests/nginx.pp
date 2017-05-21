class pogodynka::nginx {
  class {
    '::nginx':
  } ->
  file {
    '/etc/nginx/sites-enabled/pogodynka.pietraszek.pl':
      source => 'puppet:///modules/pogodynka/nginx.conf';
  }
}
