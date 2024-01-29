UPDATE `user`.`user`
SET
`email` = <{email: }>,
`username` = <{username: }>,
`password` = <{password: }>,
`supervisor` = <{supervisor: }>
WHERE `email` = <{expr}>;
