const title = (s) => {
    window.document.title = "Original - " + s
}
let theme = localStorage.getItem("theme");
if (theme === undefined || theme === null) {
    theme = "lumen";
    localStorage.setItem("theme", theme);
}
const head = $('head');
switch (theme) {
    case "lumen":
        head.append('<link rel="stylesheet" href="https://bootswatch.com/4/lumen/bootstrap.min.css">');
        break;
    case "solar":
        head.append('<link rel="stylesheet" href="https://bootswatch.com/4/solar/bootstrap.min.css">');
        break;
    case "cyborg":
        head.append('<link rel="stylesheet" href="https://bootswatch.com/4/cyborg/bootstrap.min.css">');
        break;
    case "minty":
        head.append('<link rel="stylesheet" href="https://bootswatch.com/4/minty/bootstrap.min.css">');
        break;
    case "sketchy":
        head.append('<link rel="stylesheet" href="https://bootswatch.com/4/sketchy/bootstrap.min.css">');
        break;

}

const loadThemeSystem = () => {
    const lumen = $('#lumen');
    const solar = $('#solar');
    const cyborg = $('#cyborg');
    const minty =$('#minty');
    const sketchy =$('#sketchy');
    const checkFonction = () => {
        switch (theme) {
            case "lumen":
                lumen.prop("checked", true);
                break;
            case "solar":
                solar.prop("checked", true);
                break;
            case "cyborg":
                cyborg.prop("checked", true);
                break;
            case "minty":
                minty.prop("checked", true);
                break;
            case "sketchy":
                sketchy.prop("checked", true);
                break;
        }
    };
    checkFonction();
};

const onChangeTheme = (s) => {
    localStorage.setItem("theme", s);
    location.reload();
}

