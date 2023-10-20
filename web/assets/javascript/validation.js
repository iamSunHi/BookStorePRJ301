$().ready(function () {
    $.validator.addMethod(
            "validatePassword",
            function (value, element) {
                return (
                        this.optional(element) ||
                        /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/i.test(value)
                        );
            }, "Enter a password between 8 and 16 characters including uppercase, lowercase, and at least one digit.");
    $.validator.addMethod(
            "noSpace",
            function (value, element) {
                return value.indexOf(" ") < 0;
            }, "Invalid username!");

    $("#register").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            username: {
                required: true,
                rangelength: [6, 32],
                noSpace: true,
            },
            fullname: {
                required: true,
                rangelength: [6, 50],
            },
            password: {
                required: true,
                minlength: 8,
                validatePassword: true,
            },
            confirmpassword: {
                required: true,
                minlength: 8,
                equalTo: "#password",
            },
        },
        messages: {
            username: {
                required: "Required!",
            },
            fullname: {
                required: "Required!",
            },
            password: {
                required: "Required!",
            },
            confirmpassword: {
                required: "Required!",
                equalTo: "The passwords must be same.",
            },
        },
    });

    $("#personal-info").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            username: {
                required: true,
                rangelength: [6, 32],
                noSpace: true,
            },
            name: {
                required: true,
                rangelength: [6, 50],
            },
            email: {
                email: true,
            },
        },
        messages: {
            username: {
                required: "Required!",
            },
            name: {
                required: "Required!",
            },
        },
    });
    
    $("#login-info").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            oldpassword: {
                required: true,
                minlength: 8,
            },
            newpassword: {
                required: true,
                minlength: 8,
                validatePassword: true,
            },
            confirmpassword: {
                required: true,
                minlength: 8,
                equalTo: "#newpassword",
            },
        },
        messages: {
            oldpassword: {
                required: "Required!",
            },
            newpassword: {
                required: "Required!",
            },
            confirmpassword: {
                required: "Required!",
                equalTo: "The passwords must be same.",
            },
        },
    });
    
    $("#order").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            name: {
                required: true,
                rangelength: [6, 50],
            },
            email: {
                email: true,
                required: true,
            },
            phone: {
                required: true,
            },
            address: {
                required: true,
            }
        },
        messages: {
            name: {
                required: "The Name field is required!",
            },
            email: {
                required: "The Email field is required!",
            },
            phone: {
                required: "The PhoneNumber field is required!",
            },
            address: {
                required: "The Address field is required!",
            }
        },
    });
});
