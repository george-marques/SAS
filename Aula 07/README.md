# Configuração do PostgreSQL no FreeBSD 

## Passo 1 – Instale o PostgreSQL
instale o servidor PostgreSQL e o pacote do cliente usando o seguinte comando:

```bash
pkg install postgresql16-server postgresql16-client nano-7.2
```

Depois que ambos os pacotes estiverem instalados, habilite o serviço PostgreSQL para iniciar na reinicialização do sistema.

```bash
sysrc postgresql_enable=yes
```

A seguir, inicialize o banco de dados PostgreSQL com o seguinte comando:

```bash
/usr/local/etc/rc.d/postgresql initdb
```
Saída: 

```bash
creating directory /var/db/postgres/data16 ... ok
creating subdirectories ... ok
selecting dynamic shared memory implementation ... posix
selecting default max_connections ... 100
selecting default shared_buffers ... 128MB
selecting default time zone ... UTC
creating configuration files ... ok
running bootstrap script ... ok
performing post-bootstrap initialization ... ok
syncing data to disk ... ok
```
Em seguida, inicie o serviço PostgreSQL.

```bash
service postgresql start
```

Você pode verificar o status do serviço PostgreSQL usando o seguinte comando:

```bash
service postgresql status
```

Por padrão, o PostgreSQL escuta na porta 5432. Você pode verificar isso com o seguinte comando:

```bash
sockstat -l4 -P tcp
```

## Passo 2 – Configurar a autenticação PostgreSQL

Por padrão, o PostgreSQL oferece suporte a diferentes métodos de autenticação, como md5, RADIUS, PAM, LDAP e muito mais. Nesta seção, configuraremos a autenticação baseada em senha usando MD5.

Primeiro, edite o arquivo de configuração do PostgreSQL.

```bash
nano /var/db/postgres/data16/pg_hba.conf
```
Encontre e altere as seguintes linhas de trust para md5:

```bash
# TYPE  DATABASE        USER            ADDRESS                 METHOD

# "local" is for Unix domain socket connections only
local   all             all                                     trust
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
# IPv6 local connections:
host    all             all             ::1/128                 md5
```

Salve e feche o arquivo e reinicie o serviço PostgreSQL para aplicar as alterações.

```bash
service postgresql restart
```

## Passo 3 – Crie um usuário e banco de dados no PostgreSQL
Primeiro, conecte-se ao Postgres com o seguinte comando:

```bash
su - postgres
psql
```
Em seguida, defina a senha para o usuário postgres padrão.

```bash
\password postgres
```

Em seguida, crie um novo banco de dados denominado testdb e um usuário denominado testuser.

```bash
create database testdb;
create user testuser with encrypted password 'password';
```

Em seguida, conceda todos os privilégios no banco de dados testdb ao testuser.

```bash
grant all privileges on database testdb to testuser;
```

Em seguida, verifique os usuários criados usando o seguinte comando:

```bash
\du
```

Saida:

```bash
 List of roles
 Role name |                         Attributes                         
-----------+------------------------------------------------------------
 postgres  | Superuser, Create role, Create DB, Replication, Bypass RLS
 testuser  | 
```

Agora você pode sair do shell do PostgreSQL com o seguinte comando.

```bash
\q
```
