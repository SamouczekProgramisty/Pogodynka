class pogodynka::nginx {
  include nginx

  nginx::resource::server {
    'pogodynka.kolekcja.p5.tiktalik.io':
       listen_port => 80,
       proxy       => 'http://localhost:8080'
  }
}
