class pogodynka::nginx(
  $code_dir = $::pogodynka::params::code_dir
) inherits ::pogodynka::params {
  class {
    '::nginx':
  } ->
  file {
    '/etc/nginx/sites-enabled/pogodynka.pietraszek.pl':
      content => template('pogodynka/nginx.conf.erb');
  }
}
